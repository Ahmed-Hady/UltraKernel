/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ultradevs.ultrakernel.utils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ultradevs.ultrakernel.R;

import javax.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class BatteryMeterView extends ImageView {
    @VisibleForTesting
    BatteryMeterDrawable mDrawable;
    @VisibleForTesting
    ColorFilter mErrorColorFilter;
    @VisibleForTesting
    ColorFilter mAccentColorFilter;

    public BatteryMeterView(Context context) {
        this(context, null, 0);
    }

    public BatteryMeterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BatteryMeterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final int frameColor = context.getColor(R.color.batterymeter_frame_color);
        mAccentColorFilter = new PorterDuffColorFilter(
                context.getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        mErrorColorFilter = new PorterDuffColorFilter(
                context.getColor(R.color.mono), PorterDuff.Mode.SRC_IN);

        mDrawable = new BatteryMeterDrawable(context, frameColor);
        mDrawable.setShowPercent(false);
        mDrawable.setBatteryColorFilter(mAccentColorFilter);
        mDrawable.setWarningColorFilter(
                new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN));
        setImageDrawable(mDrawable);
    }

    public void setBatteryLevel(int level) {
        mDrawable.setBatteryLevel(level);
        if (level < mDrawable.getCriticalLevel()) {
            mDrawable.setBatteryColorFilter(mErrorColorFilter);
        } else {
            mDrawable.setBatteryColorFilter(mAccentColorFilter);
        }
    }

    public int getBatteryLevel() {
        return mDrawable.getBatteryLevel();
    }

    public void setCharging(boolean charging) {
        mDrawable.setCharging(charging);
    }

    public static class BatteryMeterDrawable extends BatteryMeterDrawableBase {
        private final int mIntrinsicWidth;
        private final int mIntrinsicHeight;

        public BatteryMeterDrawable(Context context, int frameColor) {
            super(context, frameColor);

            mIntrinsicWidth = context.getResources()
                    .getDimensionPixelSize(R.dimen.battery_meter_width);
            mIntrinsicHeight = context.getResources()
                    .getDimensionPixelSize(R.dimen.battery_meter_height);
        }

        @Override
        public int getIntrinsicWidth() {
            return mIntrinsicWidth;
        }

        @Override
        public int getIntrinsicHeight() {
            return mIntrinsicHeight;
        }

        public void setWarningColorFilter(@Nullable ColorFilter colorFilter) {
            mWarningTextPaint.setColorFilter(colorFilter);
        }

        public void setBatteryColorFilter(@Nullable ColorFilter colorFilter) {
            mFramePaint.setColorFilter(colorFilter);
            mBatteryPaint.setColorFilter(colorFilter);
            mBoltPaint.setColorFilter(colorFilter);
        }
    }

}