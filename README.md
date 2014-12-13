# android-appwidget-cirrus

Copyright © 2014 Wayne D Grant
W
Licensed under the MIT License

Android App Widget to display [Weather Display Live](http://www.weather-display.com/wdlive.php) data. Written in Java.

## Overview

android-appwidget-cirrus is an Android App Widget that displays data from any WD Live clientraw.txt file available on the web via http.

android-appwidget-cirrus supports the display of many different weather items and measurement units.

![alt tag](res/drawable-nodpi/preview.png)

## Installation

* Clone the Java source from github:

```
$ git clone https://github.com/waynedgrant/android-appwidget-cirrus.git
```

* Install the Android Developer Tools (ADT)
* Start the Eclipse ADT and import the cloned directory as an existing Android project
* Run as an Android application to install to a chosen Android device connected to your computer

## App Widget Help

To add the widget locate 'Cirrus 2 x 2' in your device's Widget gallery. Add one widget per weather station you want to display readings from.

On adding the Cirrus widget its Settings page will be automatically displayed for initial configuration.

### Settings

* **Client Raw URL** - WD Live client raw url. e.g. http://www.waynedgrant.com/weather/meteohub/clientraw.txt
* **Station Name** - Optionally overwrite the displayed 'Station Name' fetched from WD Live
* **Weather Items** - Configure five weather items to display on Widget (see below)
* **Meaurement Units** - Select displayed measurement units (see below)
* **Date/Time Display** - Configure the display format of 'Last Update Time'
* **Appearanace** - Configure transparency of widget
* **Weather Update** - Configure update network related timeouts

Touch **OK** at the bottom of the settings screen to save Settings selections.

## Widget Controls

'Station Name', 'Outdoor Temperature' and 'Last Update Time' are always displayed on the widget along with the five Weather Items configured in Settings.

* Touch the spanner icon to update the widget's settings
* If displayed touch the warning icon to see details of any current connection issues
* Touch anywhere else on the widget and it will attempt to update and display the latest data from WD Live
* Otherwise the widget will attempt to update and display the latest data from WD Live every 30 minutes

## Weather Items

* Apparent Temperature
* Average Wind
* Blank
* Daily Rainfall
* Dew Point
* Forecast
* Gust
* Heat Index
* Humidex
* Humidity
* Indoor Conditions
* Rainfall Rate
* Solar
* Surface Pressure
* UV Index
* Wind Chill

## Measurement Units

### Temperature

* Celsius (°C)
* Fahrenheit (°F)

### Surface Pressure

* Hectopascals (hPa)
* Inches of Mercury (inHg)
* Kilopascals (kPa)
* Millibars (mb)
* Millimetres of Mercury (mmHg)

### Wind Speed

* Beaufort Scale (Bft)
* Kilometres per Hour (km/h)
* Knots (kts)
* Metres per Second (m/s)
* Miles per Hour (mph)

### Wind Direction

* Cardinal Direction (16 points)
* Compass Degreess

### Rainfall

* Inches
* Millimetres
