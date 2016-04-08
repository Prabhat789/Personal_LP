package com.mobisys.aspr.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by Prabhat on 06/04/16.
 */

@ParseClassName("AshaGallery")
public class Gallery extends ParseObject {

    public ParseFile getImage() {
        return getParseFile("ImageFile");
    }
    public void setImage(ParseFile postImage) {
        put("ImageFile", postImage);
    }



}
