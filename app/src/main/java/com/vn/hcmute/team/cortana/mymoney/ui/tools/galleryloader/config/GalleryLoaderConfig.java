package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.config;

import android.os.Parcel;
import android.os.Parcelable;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader.ImageLoader;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import java.util.List;

/**
 * Created by infamouSs on 8/22/17.
 */

public class GalleryLoaderConfig implements Parcelable{
    
    
    private List<ImageGallery> selectedIamges;
    private String folderTitle;
    private String imageTitle;
    private int mode;
    private int limit;
    private int theme;
    private boolean folderMode;
    private ImageLoader imageLoader;
    
    public GalleryLoaderConfig(){
        
    }
    
    protected GalleryLoaderConfig(Parcel in) {
        selectedIamges = in.createTypedArrayList(ImageGallery.CREATOR);
        folderTitle = in.readString();
        imageTitle = in.readString();
        mode = in.readInt();
        limit = in.readInt();
        theme = in.readInt();
        folderMode = in.readByte() != 0;
        this.imageLoader = (ImageLoader) in.readSerializable();
    }
    
    public static final Creator<GalleryLoaderConfig> CREATOR = new Creator<GalleryLoaderConfig>() {
        @Override
        public GalleryLoaderConfig createFromParcel(Parcel in) {
            return new GalleryLoaderConfig(in);
        }
        
        @Override
        public GalleryLoaderConfig[] newArray(int size) {
            return new GalleryLoaderConfig[size];
        }
    };
    
    public List<ImageGallery> getSelectedIamges() {
        return selectedIamges;
    }
    
    public void setSelectedIamges(
              List<ImageGallery> selectedIamges) {
        this.selectedIamges = selectedIamges;
    }
    
    public String getFolderTitle() {
        return folderTitle;
    }
    
    public void setFolderTitle(String folderTitle) {
        this.folderTitle = folderTitle;
    }
    
    public String getImageTitle() {
        return imageTitle;
    }
    
    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
    
    public int getMode() {
        return mode;
    }
    
    public void setMode(int mode) {
        this.mode = mode;
    }
    
    public int getLimit() {
        return limit;
    }
    
    public void setLimit(int limit) {
        this.limit = limit;
    }
    
    public int getTheme() {
        return theme;
    }
    
    public void setTheme(int theme) {
        this.theme = theme;
    }
    
    public boolean isFolderMode() {
        return folderMode;
    }
    
    public void setFolderMode(boolean folderMode) {
        this.folderMode = folderMode;
    }
    
    public ImageLoader getImageLoader() {
        return imageLoader;
    }
    
    public void setImageLoader(
              ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }
    
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    
        dest.writeTypedList(selectedIamges);
        dest.writeString(folderTitle);
        dest.writeString(imageTitle);
        dest.writeInt(mode);
        dest.writeInt(limit);
        dest.writeInt(theme);
        dest.writeByte((byte) (folderMode ? 1 : 0));
        dest.writeSerializable(this.imageLoader);
    }
    

}
