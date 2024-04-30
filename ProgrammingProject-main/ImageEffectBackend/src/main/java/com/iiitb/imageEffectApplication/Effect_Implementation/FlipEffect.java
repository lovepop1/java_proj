package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import libraryInterfaces.FlipInterface;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.Pixel;
import com.iiitb.imageEffectApplication.service.LoggingService;

import java.util.ArrayList;

public class FlipEffect extends FlipInterface implements DiscreteEffect
{
	int toggleHorizontalFlip;
	int toggleVerticalFlip;

	public FlipEffect()
	{
		toggleHorizontalFlip=0;
		toggleVerticalFlip=0;
	}

	@Override
	public void selectOptionValue(String optionName, int value) throws IllegalParameterException
	{
		//We do uppercase here because we aren't sure about the contents of the string optionName. 
		optionName = optionName.toUpperCase();
		if(optionName.contains("HORIZONTAL"))
		{
			toggleHorizontalFlip = value;
		}
		else if(optionName.contains("VERTICAL"))
		{
			toggleVerticalFlip = value;
		}
	}

	//We call the apply method only after setting the values of the toggle buttons. so, no need to pass them in the parameter list.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyFlip(image,toggleHorizontalFlip,toggleVerticalFlip));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Flip","Toggle Horizontal Flip: " + Integer.toString(toggleHorizontalFlip) + ", Toggle Vertical Flip: " + Integer.toString(toggleVerticalFlip));
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
