package com.joey.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.joey.R;

/**
 * Created by Joey on 2016/11/28.
 */
public class JAlertDialog extends Dialog {
    public boolean autoDismiss = true;

    public JAlertDialog(Context context) {
        super(context);
    }

    public JAlertDialog(Context context, int theme) {
        super(context, theme);
    }

    protected JAlertDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 建造者类
     *
     * @author Administrator
     */
    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private String neutralButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private OnClickListener neutralButtonClickListener;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置消息内容
         *
         * @param message
         * @return
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置消息标题
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        /**
         * 设置积极按钮
         *
         * @param positiveButtonText
         * @param listener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText,
                OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置消极按钮
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置中立按钮
         *
         * @param negativeButtonText
         * @param listener
         * @return
         */
        public Builder setNeutralButton(String negativeButtonText,
                                        OnClickListener listener) {
            this.neutralButtonText = negativeButtonText;
            this.neutralButtonClickListener = listener;
            return this;
        }

        /**
         * 设置积极按钮
         *
         * @param positiveRes
         * @param listener
         * @return
         */
        public Builder setPositiveButton(int positiveRes,
                                         OnClickListener listener) {
            this.positiveButtonText = context.getResources().getString(positiveRes);
            this.positiveButtonClickListener = listener;
            return this;
        }

        /**
         * 设置消极按钮
         *
         * @param negativeintRes
         * @param listener
         * @return
         */
        public Builder setNegativeButton(int negativeintRes,
                                         OnClickListener listener) {
            this.negativeButtonText = context.getResources().getString(negativeintRes);
            this.negativeButtonClickListener = listener;
            return this;
        }

        /**
         * 设置中立按钮
         *
         * @param neutralRes
         * @param listener
         * @return
         */
        public Builder setNeutralButton(int neutralRes,
                                        OnClickListener listener) {
            this.neutralButtonText = context.getResources().getString(neutralRes);
            this.neutralButtonClickListener = listener;
            return this;
        }

        /**
         * 创建一个AlertDialog
         *
         * @return
         */
        public JAlertDialog create() {
            LayoutInflater inflater = LayoutInflater.from(context);

            final JAlertDialog dialog = new JAlertDialog(context,
                    R.style.mydialog);

            View layout = null;
            layout = inflater.inflate(R.layout.dlg_alert_layout_1, null);

            if (null != contentView) {
                ViewGroup viewGroup = (ViewGroup) layout.findViewById(R.id.dlg_content_layout);

                // 设置对话框的视图
                LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                viewGroup.addView(contentView, params);
                contentView.setVisibility(View.VISIBLE);

            }
            // 设置标题
            TextView titleView = (TextView) layout.findViewById(R.id.dlg_title_text);
            if (null == title) {
                titleView.setVisibility(View.GONE);
            } else {
                titleView.setText(title);
                titleView.setVisibility(View.VISIBLE);

            }
            // 设置内容
            TextView messageView = (TextView) layout.findViewById(R.id.dlg_msg_text);
            if (null == message) {
                messageView.setVisibility(View.GONE);
            } else {
                messageView.setText(message);
                messageView.setVisibility(View.VISIBLE);
            }

            // 设置积极按钮
            Button positiveButton = (Button) layout
                    .findViewById(R.id.dlg_btn_positive);
            if (null == positiveButtonText) {
                positiveButton.setVisibility(View.GONE);
            } else {
                positiveButton.setText(positiveButtonText);
                positiveButton.setVisibility(View.VISIBLE);
                positiveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(dialog.autoDismiss)
                            dialog.dismiss();
                        positiveButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            // 设置消极按钮
            Button negativeButton = (Button) layout
                    .findViewById(R.id.dlg_btn_negative);
            if (null == negativeButtonText) {
                negativeButton.setVisibility(View.GONE);
            } else {
                negativeButton.setVisibility(View.VISIBLE);
                negativeButton.setText(negativeButtonText);
                negativeButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if(dialog.autoDismiss)
                            dialog.dismiss();
                        negativeButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }

//             设置中立按钮
            Button neutralButton = (Button) layout
                    .findViewById(R.id.dlg_btn_neutral);
            if (null == neutralButtonText) {
                neutralButton.setVisibility(View.GONE);
            } else {
                layout.findViewById(R.id.img_line).setVisibility(View.GONE);
                neutralButton.setVisibility(View.VISIBLE);
                neutralButton.setText(neutralButtonText);
                neutralButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if(dialog.autoDismiss)
                              dialog.dismiss();
                        neutralButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEUTRAL);
                    }
                });
            }

            // 设置对话框的视图
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            dialog.setContentView(layout, params);
            return dialog;
        }
    }

}
