package edu.unicen.surfforecaster.server.domain.entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.OneToMany;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import edu.unicen.surfforecaster.server.domain.WaveWatchModel;
import edu.unicen.surfforecaster.server.domain.weka.strategy.GenerationStrategy;

public class WekaForecaster extends Forecaster{
	@OneToMany
	private List<Forecast> archivedForecasts = new ArrayList<Forecast>();
	
	private Classifier classifier;

	private String modelFileName = this.getName()+"-"+classifier.getClass().getName()+".model";

	private WaveWatchModel model;

	private VisualObservationsSet observations;

	private GenerationStrategy strategy;
	
	public WekaForecaster(Classifier cl, GenerationStrategy st, WaveWatchModel model,VisualObservationsSet visualObservations){
		this.classifier = cl;
		this.model = model;
		this.observations = visualObservations;
		this.strategy = st;
		Date from  = observations.getFromDate();
		Date to = observations.getToDate();
		model.getArchivedForecasts(null, null, null);
		st.generateTrainningData(null);
//		classifier.buildClassifier();
//		Evaluation e = new Evaluatsion(instances);
		this.saveClassifier();		
	}
	
	private void saveClassifier() {
		// save the model file
		OutputStream os;
		try {
			os = new FileOutputStream(modelFileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(os);
			objectOutputStream.writeObject(classifier);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}
	
	public Classifier getClassifier(){
		if (this.getClassifier() == null){
			//read the model file
			InputStream is;
			try {
				is = new FileInputStream(modelFileName);
				ObjectInputStream objectInputStream;
				objectInputStream = new ObjectInputStream(is);
				classifier = (Classifier) objectInputStream.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		return this.classifier;
	}
	
	@Override
	public String getDescription() {
		return "WEKA Forecaster";
	}

	@Override
	public Collection<Forecast> getArchivedForecasts(Date from, Date to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Forecast> getLatestForecasts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


}
