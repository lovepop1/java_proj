package com.iiitb.imageEffectApplication.Effect_Implementation;

import com.iiitb.imageEffectApplication.baseEffects.*;
import libraryInterfaces.RotationInterface;
import com.iiitb.imageEffectApplication.exception.IllegalParameterException;
import libraryInterfaces.Pixel;
import com.iiitb.imageEffectApplication.service.LoggingService;

import java.util.ArrayList;

public class RotationEffect extends RotationInterface implements SingleValueDiscreteEffect 
{
	int rotateBy;

	public RotationEffect()
	{
		rotateBy=0;
	}

	@Override
	public void setParameterValue(int parameterValue) throws IllegalParameterException
	{
		rotateBy=parameterValue;
	}

	//We call the apply method only after setting the value of the rotation, so no need to pass it in the parameter list.
	@Override
	public Pixel[][] apply(Pixel[][] image,String fileName,LoggingService loggingService)
	{
		//Using threading to perform image processing, logging simultaneously.
		//The image_processing thread processes the image and adds it to a list.
		ArrayList<Pixel[][]> result = new ArrayList<Pixel[][]>();
		Thread image_processing = new Thread(()-> {
			result.add(applyRotation(image,rotateBy));
		});

		//The logging thread performs the addLog function to add to the logs about this activity.
		Thread logging=new Thread(()->{
			loggingService.addLog(fileName,"Rotation", 90*Integer.toString(rotateBy) + "°");
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
