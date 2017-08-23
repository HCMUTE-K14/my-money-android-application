package com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.helper.imageloader;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.listener.ImageLoaderListener;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.Folder;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ImageGallery;
import com.vn.hcmute.team.cortana.mymoney.ui.tools.galleryloader.model.ReturnGalleryValue;
import io.reactivex.Observable;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

/**
 * Created by infamouSs on 8/23/17.
 */

public class ImageFileLoader {
    
    private Context mContext;
    
    @Inject
    public ImageFileLoader(Context context) {
        this.mContext = context;
    }
    
    private final String[] projections = new String[]{
              MediaStore.Images.Media._ID,
              MediaStore.Images.Media.DISPLAY_NAME,
              MediaStore.Images.Media.DATA,
              MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
              MediaStore.Images.Media.DATE_TAKEN
    };
    
    public void loadDeviceImages(final boolean isFolderMode, final ImageLoaderListener listener) {
        new ImageLoadRunnable(isFolderMode, listener).get();
    }
    
    
    private class ImageLoadRunnable {
        
        private boolean isFolderMode;
        private ImageLoaderListener listener;
        
        public ImageLoadRunnable(boolean isFolderMode, ImageLoaderListener listener) {
            this.isFolderMode = isFolderMode;
            this.listener = listener;
        }
    
    
        public void get() {
            Cursor cursor = mContext.getContentResolver()
                      .query(Images.Media.EXTERNAL_CONTENT_URI, projections, null, null,
                                MediaStore.Images.Media.DATE_ADDED);
    
            if (cursor == null) {
                listener.onFailure(new NullPointerException());
                return;
            }
            List<ImageGallery> images = new ArrayList<>();
    
            Map<String, Folder> folderMap = null;
            if (isFolderMode) {
                folderMap = new HashMap<>();
            }
    
            if (cursor.moveToLast()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(projections[0]));
                    String name = cursor.getString(cursor.getColumnIndex(projections[1]));
                    String path = cursor.getString(cursor.getColumnIndex(projections[2]));
                    String bucket = cursor.getString(cursor.getColumnIndex(projections[3]));
                    String date = cursor.getString(cursor.getColumnIndex(projections[4]));
            
                    File file = makeSafeFile(path);
                    if (file != null && file.exists()) {
                        ImageGallery image = new ImageGallery(id, name, date, path);
                        images.add(image);
                
                        if (folderMap != null) {
                            Folder folder = folderMap.get(bucket);
                            if (folder == null) {
                                folder = new Folder(bucket);
                                folderMap.put(bucket, folder);
                            }
                            folder.getImages().add(image);
                        }
                    }
            
                } while (cursor.moveToPrevious());
            }
            cursor.close();
    
            List<Folder> folders = null;
            if (folderMap != null) {
                folders = new ArrayList<>(folderMap.values());
            }
            
            ReturnGalleryValue result=new ReturnGalleryValue();
            result.setFolders(folders);
            result.setImages(images);
    
            
            listener.onLoaded(Observable.just(result));
        }
    }
    private static File makeSafeFile(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        try {
            return new File(path);
        } catch (Exception e) {
            return null;
        }
    }
}
