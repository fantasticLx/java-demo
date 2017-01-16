/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image.api;

import net.coobird.thumbnailator.geometry.Positions;

/**
 * @author Victor Zhang
 * @date 2017/1/16.
 */
public class ImageParamDTO {
    private Integer width;
    private Integer height;
    private Float scale;
    private Integer rotate;
    private Float quality;
    private String suffix;
    private String wartermarkPath;
    private Float wartermarkOpacity;
    private Positions wartermarkPosition;

    public Float getScale() {
        return scale;
    }

    public void setScale(Float scale) {
        this.scale = scale;
    }

    public Float getWartermarkOpacity() {
        return wartermarkOpacity;
    }

    public void setWartermarkOpacity(Float wartermarkOpacity) {
        this.wartermarkOpacity = wartermarkOpacity;
    }

    public Positions getWartermarkPosition() {
        return wartermarkPosition;
    }

    public void setWartermarkPosition(Positions wartermarkPosition) {
        this.wartermarkPosition = wartermarkPosition;
    }


    public String getWartermarkPath() {
        return wartermarkPath;
    }

    public void setWartermarkPath(String wartermarkPath) {
        this.wartermarkPath = wartermarkPath;
    }

    public Float getQuality() {
        return quality;
    }

    public void setQuality(Float quality) {
        this.quality = quality;
    }


    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getRotate() {
        return rotate;
    }

    public void setRotate(Integer rotate) {
        this.rotate = rotate;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
