#include"Rotation.h"

//Function to apply rotation on the image. The image is only rotated by either of the 4 values possible.
void apply_rotation(vector<vector<Pixel>> &imageVector,int rotateBy)
{
    //The value of rotateBy is either 0 or 90 or 180 or 270 only. We rotate anti-clockwise. In case of clockwise rotation, we simply have to interchange the if statements of rotateBy being equal to 90,270
    if(rotateBy==0 || (rotateBy!=1 && rotateBy!=2 && rotateBy!=3))
    {
        return;
    }

    //Get the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //The rotated image will have it's rows and cols to be the number of cols and rows in the original image if the image is to be rotated by 90 or 270 degrees. It will have the same number of rows and columns in case the image is to be rotated by 90 degrees.
    int new_rows=c_size;
    int new_cols=r_size;
    if(rotateBy==2)
    {
        new_rows=r_size;
        new_cols=c_size;
    }

    //Creating a new image to hold the rotated image.
    vector<vector<Pixel>> rotatedImage(new_rows,vector<Pixel>(new_cols));

    //The logic as required for rotating the image by 90 degrees or 180 degrees or 270 degrees.
    if(rotateBy==1)
    {
        for(int i=0;i<r_size;i++)
        {
            for(int j=0;j<c_size;j++)
            {
                  rotatedImage[c_size-1-j][i]=imageVector[i][j];
            }
        }
    }
    else if(rotateBy==2)
    {
        for(int i=0;i<r_size;i++)
        {
            for(int j=0;j<c_size;j++)
            {
                rotatedImage[r_size-1-i][c_size-1-j]=imageVector[i][j];
            }
        }
    }
    else if(rotateBy==3)
    {
        for(int i=0;i<r_size;i++)
        {
            for(int j=0;j<c_size;j++)
            {
                rotatedImage[j][r_size-1-i]=imageVector[i][j];
            }
        }
    }

    imageVector=rotatedImage;
}
