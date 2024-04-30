#include"Sharpen.h"

//Function to apply sharpen effect
void apply_sharpen(vector<vector<Pixel>> &imageVector,float amount)
{
    //Returning the image without processing if the value of amount is not in the valid range.
    if(amount<0 || amount>100 || amount==50)
    {
        return;
    }

    //The below statement is to just ensure that there is equal amount of effect applied for diff values of amount.
    amount+=15;

    //Getting the dimensions of the imageVector.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Creating the kernel
    vector<vector<int>> Kernel=
    {{0,-1,0},
    {-1,5,-1},
    {0,-1,0}
    };

    //Getting the dimensions of the kernel.
    int rk_size=Kernel.size();
    int ck_size=Kernel[0].size();

    //Updating the kernal value based on amount.
    for(int i=0;i<rk_size;i++)
    {
        for(int j=0;j<ck_size;j++)
        {
            Kernel[i][j]*=(amount/100.0);
        }
    }

    //Making a result imageVector.
    vector<vector<Pixel>> sharpenedImage(r_size,vector<Pixel>(c_size));

    //Iterating through all the pixels of the image which are surrounded by pixels on all sides.
    for(int x=1;x<c_size-1;x++)
    {
        for(int y=1;y<r_size-1;y++)
        {
            //Creating a reference pixel.
            Pixel &pixel=sharpenedImage[y][x];

            //Iterating through the kernel and applying convolution.
            for(int dx=-1;dx<=1;dx++)
            {
                for(int dy=-1;dy<=1;dy++)
                {
                    int nx=x+dx;
                    int ny=y+dy;
                    pixel.r+=Kernel[dx+1][dy+1]*imageVector[ny][nx].r;
                    pixel.g+=Kernel[dx+1][dy+1]*imageVector[ny][nx].g;
                    pixel.b+=Kernel[dx+1][dy+1]*imageVector[ny][nx].b;
                }
            }

            //Updating the rgb values of the pixels.
            pixel.r=max(0,min(255,pixel.r));
            pixel.g=max(0,min(255,pixel.g));
            pixel.b=max(0,min(255,pixel.b));
        }
    }

    //Copying the result image into the imageVector.
    imageVector=sharpenedImage;
}