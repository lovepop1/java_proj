#include "HueSaturation.h"

//Function to apply hue saturation to the image.
void apply_hue_saturation(vector<vector<Pixel>> &imageVector,float satVal,float hueVal)
{
    //Returning the image without going executing the code if the saturation value is 100 and hue value is 0.
    if(satVal==100 && hueVal==0)
    {
        return;
    }

    //Copying the imageVector into a resultant image.
    auto resultImage=imageVector;

    //Reducing the satVal to match with the below code.
    satVal/=100;

    //Getting the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Converting degree to radian.
    float cosA=cos(3.6*M_PI*hueVal/180);
    float sinA=sin(3.6*M_PI*hueVal/180);

    //Calculating the rotation matrix based off the hue val.
    float matrix[3][3] = {
              {cosA+((1-cosA)/3.0f), (1/3.0f)*(1-cosA)-sqrt(1/3.0f)*sinA, (1/3.0f)*(1-cosA)+sqrt(1/3.0f) * sinA},
              {(1/ 3.0f)*(1-cosA)+sqrt(1/3.0f)*sinA, cosA+(1/3.0f)*(1-cosA),(1/3.0f)*(1-cosA)-sqrt(1/3.0f) * sinA},
              {(1/ 3.0f)*(1-cosA)-sqrt(1/3.0f)*sinA, (1/3.0f)*(1-cosA)+sqrt(1/3.0f) * sinA, cosA + (1/3.0f)*(1-cosA)}
    };

    //Iterating over all the pixels of the image.
    for(int i=0;i<r_size;i++)
    {
        for(int j=0;j<c_size;j++)
        {
            //Temporary arrays to hold the updated pixel values and the old pixel values.
            int values[3];
            int ref_values[3]={resultImage[i][j].r,resultImage[i][j].g,resultImage[i][j].b};

            //Calculating the new rgb values.
            for(int r=0;r<3;r++)
            {
                int tmp;
                for(int c=0;c<3;c++)
                {
                    tmp += ref_values[c] * matrix[r][c];
                }
                values[r]=tmp;
            }

            //weights from CCIR 601 spec.
            float gray = 0.2989*ref_values[0] + 0.587*ref_values[1] + 0.1140*ref_values[2];

            //Clamping the resultant values to the valid range of rgb values..
            resultImage[i][j].r = max(0,min(255,static_cast<int>(-gray*satVal + (ref_values[0]*(1+satVal)))));
            resultImage[i][j].g = max(0,min(255,static_cast<int>(-gray*satVal + (ref_values[1]*(1+satVal)))));
            resultImage[i][j].b = max(0,min(255,static_cast<int>(-gray*satVal + (ref_values[2]*(1+satVal)))));
        }
    }

    //Copying the processed image into the imageVector.
    imageVector=resultImage;
}