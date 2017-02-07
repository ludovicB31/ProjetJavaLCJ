package org.geotools.tutorial.quickstart;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.sun.media.jai.util.Rational;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class newImage {
	public String name;
	public int anne;
	public int mois;
	public Date date;
	public double lati;
	public double longi;
	public File image;
	
	
	public newImage() throws ImageProcessingException, MetadataException, IOException{
		
		int year=(Integer) 0;
		int month=(Integer) 0;
		double latitude= 0.00;
		double longitude= 0.00;
		Date date=null;
		JFileChooser dialogue = new JFileChooser(new File("."));
		PrintWriter sortie;
		File fichier=null;
		File jpgFile=null;
		if (dialogue.showOpenDialog(null)== JFileChooser.APPROVE_OPTION) {
		    fichier = dialogue.getSelectedFile();
		    sortie = new PrintWriter
			(new FileWriter(fichier.getPath(), true));

		    sortie.close();}
		if(fichier != null){		 jpgFile = fichier;}
		Metadata metadata = ImageMetadataReader.readMetadata( fichier );
        Directory directory = metadata.getDirectory( ExifDirectory.class );
        final GpsDirectory gpsDirectory = (GpsDirectory) metadata.getDirectory(GpsDirectory.class);
    	if (gpsDirectory instanceof GpsDirectory) {
    	    final GpsDirectory gps = (GpsDirectory) gpsDirectory;
    	    final com.drew.lang.Rational[] lat = gps.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
    	     latitude = lat[0].doubleValue() + lat[1].doubleValue()
    		    / 60 + lat[2].doubleValue() / 3600;
    	    final com.drew.lang.Rational[] lng = gps.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
    	     longitude = lng[0].doubleValue() + lng[1].doubleValue() / 60 + lng[2].doubleValue() / 3600;
    	   
    	}
    if( directory != null )
    {
        //Read the date
         date = directory.getDate( ExifDirectory.TAG_DATETIME );
        DateFormat df = DateFormat.getDateInstance();
        df.format( date );
         year = df.getCalendar().get( Calendar.YEAR );
         month = df.getCalendar().get( Calendar.MONTH ) + 1;


       
    }
    else
    {
        System.out.println( "EXIF is null" );
    }
    this.image=fichier;
    this.anne=year;
    this.mois=month;
    this.name=fichier.getName();
    this.lati=latitude;
    this.longi=longitude;
    this.date=date;
    
    
	}
	
	public void exifViewer(newImage ni){
	    System.out.println(this.name+    "                         : nom du fichier image");
	    System.out.println(this.anne+    "                               : année de la prise de vue");
	    System.out.println(this.date+    "       : date complete de la prise de vue");
	    System.out.println(this.mois+    "                                 :  mois de la prise de vue");
	    System.out.println(this.lati+    "                 : lattitude");
	    System.out.println(this.longi+   "                 : longitude");
	    System.out.println(this.image);

		
	}
	public Date getDate(newImage n){
		return this.date;
	}
	public String getNom(newImage n){
		return this.name;
	}
	
	public double getLatitude(newImage n){
		return this.lati;
	}
	
	public double getLongitude(newImage n){
		return this.longi;
	}
	
	public static void main(String[] args) throws ImageProcessingException, MetadataException, IOException{
		newImage test=new newImage();
        test.exifViewer(test);	
        System.out.println(test.getDate(test));
	}
}







































