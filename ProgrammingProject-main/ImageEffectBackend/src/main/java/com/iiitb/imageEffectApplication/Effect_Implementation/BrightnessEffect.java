package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import com.iiitb.imageEffectApplication.service.LoggingService;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.BrightnessInterface;
import libraryInterfaces.Pixel;
import java.util.ArrayList;
import com.iiitb.imageEffectApplication.service.LoggingService;

public class BrightnessEffect extends BrightnessInterface implements SingleValueParameterizableEffect
{
	float amount;

	public BrightnessEffect()
	{
		amount=0f;
	}

	public void setParameterValue(float parameterValue) throws IllegalParameterException
	{
		amount = parameterValue;
	}

	//We call apply only after setting the value of the amount, so we don't have to pass it in the parameter list.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyBrightness(image,amount));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Brightness","Brightness Slider: " + Float.toString(amount));
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
