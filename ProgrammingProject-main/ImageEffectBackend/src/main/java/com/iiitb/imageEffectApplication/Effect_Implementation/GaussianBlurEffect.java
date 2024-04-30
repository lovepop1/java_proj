package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import libraryInterfaces.GaussianBlurInterface;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.Pixel;
import com.iiitb.imageEffectApplication.service.LoggingService;

import java.util.ArrayList;

public class GaussianBlurEffect extends GaussianBlurInterface implements SingleValueParameterizableEffect 
{
	float radius;

	public GaussianBlurEffect()
	{
		radius=0f;
	}

	@Override
	public void setParameterValue(float parameterValue) throws IllegalParameterException
	{
		radius = parameterValue;
	}

	//We call the apply method only after setting the value of radius, so no need to pass radius in the parameter list.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyGaussianBlur(image,radius));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Gaussian Blur","Gaussian Blur Radius: " + Float.toString(radius));
		});

		//Starting the threads.
		image_processing.start();
		logging.start();

		//Waiting for completion of both the threads.
		try
		{
			image_processing.join();
			logging.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}

		//Returning the processed image.
		return result.get(0);
	}

}
