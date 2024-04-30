#include"Flip.h"

//Function to apply horizontal and vertical flipping to an image.
void apply_flip(vector<vector<Pixel>> &imageVector, int toggle_Horizontal, int toggle_Vertical)
{
    //Get the dimensions of the image.
    int r_size=imageVector.size();
    int c_size=imageVector[0].size();

    //Applying the vertical toggle if it has been selected and passing the necessary parameters.
    if(toggle_Vertical==1)
    {
        apply_toggle(imageVector,r_size,c_size/2,0,c_size-1,1,-1);
    }

    //Applying the horizontal toggle if it has been selected and passing the necessary parameters.
    if(toggle_Horizontal==1)
    {
        apply_toggle(imageVector,r_size/2,c_size,r_size-1,0,-1,1);
    }
}

//The parameters are in the order: imageVector,row limit to iterate on, column limit to iterate on, the row index of the pixel to be swapped with, the column index of the pixel to be swapped with, multiplier on the variable of outer loop, multiplier on the variable of inner loop.
void apply_toggle(vector<vector<Pixel>> &imageVector,int row_lim,int col_lim,int r_index,int c_index,int r_mul,int c_mul)
{
    //Iterating on the required limits.
    for(int i=0;i<row_lim;i++)
    {
        for(int j=0;j<col_lim;j++)
        {
            //Swapping the required pixels.
            Pixel pixel=imageVector[i][j];
            imageVector[i][j]=imageVector[r_index+r_mul*i][c_index+c_mul*j];
            imageVector[r_index+r_mul*i][c_index+c_mul*j]=pixel;
        }
    }
}
