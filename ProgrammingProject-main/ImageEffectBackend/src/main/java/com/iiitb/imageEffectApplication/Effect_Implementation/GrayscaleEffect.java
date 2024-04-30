package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import libraryInterfaces.GrayscaleInterface;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.Pixel;
import com.iiitb.imageEffectApplication.service.LoggingService;

import java.util.ArrayList;

public class GrayscaleEffect extends GrayscaleInterface implements PhotoEffect
{
	public GrayscaleEffect()
	{}

	//No need to set any values, we simply have to apply the effect.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyGrayscale(image));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Grayscale","None");
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
