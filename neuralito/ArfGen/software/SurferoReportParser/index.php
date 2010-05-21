<?php
include('lib/simple_html_dom.php');
include('SurferoReport.php');

$DB = 'reporte';
$TABLA = 'test1';

$link = mysql_connect('localhost', 'root', 'admin');
if (!$link) {
    die('Not connected : ' . mysql_error());
}

// make foo the current db
$db_selected = mysql_select_db($DB, $link);
if (!$db_selected) {
    die ('Can\'t use reporte : ' . mysql_error());
}

// get DOM from URL or file
$html = file_get_html('http://www.elsurfero.com/elsurfero/pages/rep_todos.asp?gru=5&npa=REPORTES');

$waveReports = $html->find('td[width=140]');
$sqlInserts = 'INSERT INTO ' . $TABLA . '(ola, fecha, hora, altura, viento, temp_agua, clasificacion, lim_inf_altura, lim_sup_altura) VALUES';

$surferoReports = array();
foreach ($waveReports as $wave) {
    if ($wave->first_child () != null && $wave->first_child()->tag != 'table') {
        $parent = $wave->parent()->parent()->parent()->parent();
        
        $waveName = $parent->find('span[class=textMenu] strong', 0)->innertext;
        $waveName = trim(substr($waveName, strpos($waveName, 'REPORTE') + 9));
        $waveParams = $wave->innertext;

        $surferoReport = new SurferoReport();
        $surferoReport->setWaveName($waveName);
        $dateValue = extractValue($waveParams, 'Fecha: ');
        $surferoReport->setDate(extractDateValue($dateValue));
        $surferoReport->setTime(extractValue($waveParams, 'Hora: ', 5));
        $surferoReport->setWindSpeed(extractValue($waveParams, 'Vientos: '));
        $surferoReport->setWindDirection(extractValue($waveParams, 'Vientos: '));
        $surferoReport->setWaterTemperature(extractValue($waveParams, 'Temp. agua: ', 3));
        $surferoReport->setWaveClasification(extractValue($waveParams, 'Olas: '));
        $waveHeight = extractValue($waveParams, 'Swell: ');
        $waveHeight = extractWaveHeightValues($waveHeight);
        
        $surferoReport->setWaveAvgHeight($waveHeight['avg']);
        $surferoReport->setWaveMaxHeight($waveHeight['max']);
        $surferoReport->setWaveMinHeight($waveHeight['min']);

        if (strtotime($surferoReport->getDate()) >= strtotime(date('Y-m-d'))) {
            $sqlInserts .= "('"
                . $surferoReport->getWaveName() . "', '"
                . $surferoReport->getDate() . "', '"
                . $surferoReport->getTime() . "', "
                . $surferoReport->getWaveAvgHeight(). ", '"
                . $surferoReport->getWindDirection(). "', '"
                . $surferoReport->getWaterTemperature(). "', '"
                . $surferoReport->getWaveClasification(). "', "
                . $surferoReport->getWaveMinHeight(). ", "
                . $surferoReport->getWaveMaxHeight(). "), ";

            $surferoReports[] = $surferoReport;
        }
    }
}

$sqlInserts = substr($sqlInserts, 0, strlen($sqlInserts) - 2) . ';';
echo 'Observaciones para insertar: <br /><br />' . $sqlInserts. '<br /><br /><br />';

if (count($surferoReports)) {
    $result = mysql_query("SELECT id FROM " . $TABLA . " WHERE fecha = '" . $surferoReports[0]->getDate() . "'", $link);
    if (!mysql_num_rows($result)) {
        $result = mysql_query($sqlInserts, $link);
        if (!$result) {
            die('Invalid query: ' . mysql_error());
        } else {
            echo 'Las observaciones se cargaron correctamente!!!!';
        }

        $result = mysql_query("INSERT INTO pagina VALUES(0, '" . $surferoReports[0]->getDate() . "', '" . $html->plaintext . "');", $link);
        if (!$result) {
            die('Invalid query: ' . mysql_error());
        } else {
            echo 'La pagina de hoy se cargo correctamente!!!!';
        }
    } else {
        die('Ya fueron cargadas las observaciones de hoy previamente');
    }
} else {
    die('No hay observaciones del dia de la fecha disponibles, chequear si en el surfero existen y no tienen fecha o no existen directamente ');
}


/**
 *
 * @param <string> $string a long string like:
 *                  Fecha: Sin Actualizar<br />Hora: 10:00 Hs <br />Olas: regulares <br />Swell: 1 a 2 pies<br />Vientos: NO 18 Km/h<br />Temp. agua: 17 Âº <br>Fuente: J.M.A
 * @param <string> $key  the key looked for in the string like: 'Hora: '
 * @param <int or null> $valueLenght   if type= int the amount of chars of the value, for example for hora = 5. If type = null it looks for '<br />' or '<br>' to cut the substr
 * @return <string> the value according the key as param
 */
function extractValue($string, $key, $valueLenght = null) {  
  $initPart = substr($string, strpos($string, $key) + strlen($key));
    if ($valueLenght)
        return trim(substr($initPart, 0, $valueLenght));
    elseif (strpos($initPart, '<br />'))
        return trim(substr($initPart, 0, strpos($initPart, '<br />')));
    elseif (strpos($initPart, '<br>'))
        return trim(substr($initPart, 0, strpos($initPart, '<br>')));
}

/**
 *
 * @param <string> $value is like: "1 a 2 pies" or "2 pies"
 * @return <array> the average and the two limits sup and inf2
 */
function extractWaveHeightValues($value) {
    $minValue = FALSE; //trim(substr($value, 0, strpos($value, ' a')));
    $maxValue = FALSE; //trim(substr($value, strpos($value, 'a') + 1));
    
    $currentValue = '';
    for ($i = 0; $i < strlen($value) && $maxValue === FALSE; $i++) {
        $currentChar = substr($value, $i, 1);
        
        if (is_numeric($currentChar)) {
            $currentValue .= $currentChar;
        } elseif ($currentValue != '') {
            if ($minValue === FALSE)
                $minValue = $currentValue;
            else
                $maxValue = $currentValue;
            $currentValue = '';
        }
    }
    $result = array("min" => 0, "max" => 0, "avg" => 0);
    if ($maxValue !== FALSE) {
      $result = array("min" => $minValue, "max" => $maxValue, "avg" => ($maxValue + $minValue) / 2);
    } elseif ($minValue !== FALSE) {
      $result = array("min" => $minValue, "max" => $minValue, "avg" => $minValue);
    }

    return $result;
}

/**
 *
 * @param <string> $value is a date with the format: 16/05/2010
 * @return <date> with format 2010-05-16
 */
function extractDateValue($value) {
    return (substr($value, 6, 4) . '-' . substr($value, 3, 2) . '-' . substr($value, 0, 2));
}

?>