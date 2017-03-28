package com.mepty.android.libs;

import android.content.Context;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by tlinux on 17-3-14.
 */

public class LinkTextView extends RelativeLayout {

    private View contentView;
    private View linkView;

    public LinkTextView(Context context) {
        this(context,null);
    }

    public LinkTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean contentViewAddLine = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        final int childCount = getChildCount();
        if (childCount!=2) throw new IllegalArgumentException("must add two View");

        contentView = getChildAt(0);
        linkView = getChildAt(1);

        if (heightMode == MeasureSpec.EXACTLY&&getMeasuredHeight()>=height+ linkView.getMeasuredHeight()){
            return;
        }




        TextView content = findChildTextView(contentView);
        if (content == null || content.getLayout() == null) return;

        Layout layout = content.getLayout();

        if (layout.getLineWidth(layout.getLineCount()-1)+ linkView.getMeasuredWidth()> getMeasuredWidth()){
            int newHeight = getMeasuredHeight()+ linkView.getMeasuredHeight();
            setMeasuredDimension(getMeasuredWidth(),newHeight);
            contentViewAddLine = true;
        }
    }


    private TextView findChildTextView(View view){
        if (view instanceof TextView) return (TextView) view;

        while (view instanceof ViewGroup){
            ViewGroup vg = (ViewGroup) view;
            final int cc = vg.getChildCount();
            View child = vg.getChildAt(cc);
            if (child instanceof TextView) return (TextView) child;

            if (child instanceof ViewGroup){
                View find = findChildTextView(child);
                if (find!=null) return (TextView) find;
            }

        }
        return null;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        TextView tv = findChildTextView(contentView);
        if (tv == null) return;
        Layout la  = tv.getLayout();
        if (la == null) return;
        int lintRight = (int) la.getLineRight(0)+tv.getCompoundPaddingLeft();


        int bl, bt,br,bb;

        if (contentViewAddLine){

            bt= linkView.getBottom();
            bl = lintRight- linkView.getWidth();
            br = lintRight;
            bb = bt+ linkView.getHeight();

        }else{
            int lineBottom = la.getLineBottom(la.getLineCount()-1)+ tv.getCompoundPaddingTop();
            bt = lineBottom - linkView.getHeight();
            bb = lineBottom;
            if (la.getLineCount()>1){

                bl = lintRight- linkView.getWidth();
                br = lintRight;

            } else{
                bl = linkView.getLeft();
                br = linkView.getRight();
            }

        }
        linkView.layout(bl,bt,br, bb);
    }

    private CharSequence linkText ="";

    public void setLinkText(CharSequence charSequence){
        setLinkText(charSequence,true);
    }

    public void setLinkText(CharSequence charSequence,boolean underLine){
        if (!TextUtils.equals(charSequence, linkText)){
            linkText = charSequence;
            TextView tv = findChildTextView(getChildAt(1));
            tv.setText(linkText);
            if (underLine){
                if ((tv.getPaint().getFlags() & Paint.UNDERLINE_TEXT_FLAG)==0){
                    tv.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
                }
            } else {
                if ((tv.getPaint().getFlags() & Paint.UNDERLINE_TEXT_FLAG)==0){
                    tv.getPaint().setFlags(tv.getPaintFlags()&~Paint.UNDERLINE_TEXT_FLAG );
                }
            }
        }
    }

    public void setText(CharSequence charSequence){
        if (!TextUtils.equals(charSequence, linkText)){
            linkText = charSequence;
            findChildTextView(getChildAt(0)).setText(linkText);
        }
    }
}
