package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import libraryInterfaces.HueSaturationInterface;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.Pixel;
import com.iiitb.imageEffectApplication.service.LoggingService;

import java.util.ArrayList;

public class HueSaturationEffect extends HueSaturationInterface implements ParameterizableEffect 
{
	float hueAmount;
	float saturationAmount;

	public HueSaturationEffect()
	{
		hueAmount=0f;
		saturationAmount=0f;
	}

	@Override
	public void setParameter(String paramName,float parameterValue) throws IllegalParameterException
	{
		paramName = paramName.toUpperCase();
		if(paramName.contains("HUE"))
		{
			hueAmount = parameterValue;
		}
		else if(paramName.contains("SATURATION"))
		{
			saturationAmount = parameterValue;
		}
	}

	//We call apply only after setting the value of the amount, so we don't have to pass it in the parameter list.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyHueSaturation(image,saturationAmount,hueAmount));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Hue-Saturation","Hue Slider: " + Float.toString(hueAmount) + "Saturation Slider: " + Float.toString(saturationAmount));
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
