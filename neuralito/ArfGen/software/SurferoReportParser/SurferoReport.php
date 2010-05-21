<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of SurferoReport
 *
 * @author usuario
 */
class SurferoReport {

    private $waveName;
    private $date;
    private $time;
    private $waveClasification;
    private $waveAvgHeight;
    private $waveMaxHeight;
    private $waveMinHeight;
    private $windSpeed;
    private $windDirection;
    private $waterTemperature;


    public function __contruct() {}

    public function getWaveName() {
        return $this->waveName;
    }

    public function setWaveName($waveName) {
        $this->waveName = $waveName;
    }

    public function getDate() {
        return $this->date;
    }

    public function setDate($date) {
        $this->date = $date;
    }

    public function getTime() {
        return $this->time;
    }

    public function setTime($time) {
        $this->time = $time;
    }

    public function getWaveClasification() {
        return $this->waveClasification;
    }

    public function setWaveClasification($waveClasification) {
        $this->waveClasification = $waveClasification;
    }

    public function getWaveAvgHeight() {
      return $this->waveAvgHeight;
    }

    public function setWaveAvgHeight($waveAvgHeight) {
      $this->waveAvgHeight = $waveAvgHeight;
    }

    public function getWaveMaxHeight() {
      return $this->waveMaxHeight;
    }

    public function setWaveMaxHeight($waveMaxHeight) {
      $this->waveMaxHeight = $waveMaxHeight;
    }

    public function getWaveMinHeight() {
      return $this->waveMinHeight;
    }

    public function setWaveMinHeight($waveMinHeight) {
      $this->waveMinHeight = $waveMinHeight;
    }
    
    public function getWindSpeed() {
        return $this->windSpeed;
    }

    public function setWindSpeed($windSpeed) {
        $this->windSpeed = $windSpeed;
    }

    public function getWindDirection() {
        return $this->windDirection;
    }

    public function setWindDirection($windDirection) {
        $this->windDirection = $windDirection;
    }

    public function getWaterTemperature() {
        return $this->waterTemperature;
    }

    public function setWaterTemperature($waterTemperature) {
        $this->waterTemperature = $waterTemperature;
    }
    
}
?>
