package libraryInterfaces;

public class BrightnessInterface {
    static {
        String libraryPath = System.getProperty("user.dir") + "/Libraries/BrightnessLibrary/BrightnessLib.so";
//        String libraryPath = "/mnt/d/LMS_IIITBangalore/WSL_C_py/prev_sems/project/P2-2023-Project/ImageEffectBackend/Libraries/libraryInterfaces";
        System.load(libraryPath);
//        System.out.println(System.getProperty("user.dir"));
    }

    public static native Pixel[][] applyBrightness(Pixel[][] image, float amount);
}
