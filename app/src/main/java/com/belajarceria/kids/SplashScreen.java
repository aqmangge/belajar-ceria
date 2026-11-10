package com.belajarceria.kids;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Splash screen shown when the app launches:
 * Welcomes the child and parent, introduces TV remote & mouse controls,
 * and lets them jump into learning or read the guide.
 */
final class SplashScreen extends Screen {
    private final View startBtn;
    private final TextView mascot;
    private ObjectAnimator bob;

    SplashScreen(MainActivity a) {
        super(a);
        LinearLayout col = new LinearLayout(a);
        col.setOrientation(LinearLayout.VERTICAL);
        col.setGravity(Gravity.CENTER);
        col.setPadding(Ui.dp(24), Ui.dp(16), Ui.dp(24), Ui.dp(24));
        Ui.noClip(col);

        // Publisher brand tag
        TextView brand = Ui.text(a, "KHAIZURAN ACADEMY™", 13, 0xFF5B4BB7, Ui.BOLD);
        brand.setLetterSpacing(0.2f);
        brand.setBackground(Ui.round(Ui.GLASS, Ui.dp(16)));
        brand.setPadding(Ui.dp(18), Ui.dp(6), Ui.dp(18), Ui.dp(6));
        Ui.lift(brand, 3, 0xFF8A5CFF);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        bp.bottomMargin = Ui.dp(10);
        col.addView(brand, bp);

        // Cheerful mascot icon / emoji
        mascot = Ui.text(a, "🌟", 56, Ui.INK, null);
        mascot.setBackground(Ui.oval(new int[]{0xFFFFFFFF, 0xFFFFF176}));
        Ui.lift(mascot, 8, 0xFFFFB300);
        LinearLayout.LayoutParams mp = new LinearLayout.LayoutParams(Ui.dp(84), Ui.dp(84));
        mp.bottomMargin = Ui.dp(12);
        col.addView(mascot, mp);

        // Big colorful app title
        TextView title = Ui.text(a, "Belajar Ceria 🎈", 44, Ui.INK, Ui.BLACK);
        Ui.gradientText(title, Theme.current.title);
        col.addView(title, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        TextView sub = Ui.text(a, "Aplikasi Pembelajaran Kanak-Kanak Prasekolah & Tahun 1", 16, Ui.MUTED, Ui.BOLD);
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        sp.topMargin = Ui.dp(4);
        sp.bottomMargin = Ui.dp(18);
        col.addView(sub, sp);

        // Quick input indicator banner
        LinearLayout banner = new LinearLayout(a);
        banner.setOrientation(LinearLayout.HORIZONTAL);
        banner.setGravity(Gravity.CENTER);
        banner.setBackground(Ui.round(Ui.GLASS, Ui.dp(24)));
        banner.setPadding(Ui.dp(20), Ui.dp(8), Ui.dp(20), Ui.dp(8));
        Ui.lift(banner, 4, 0xFF3A86FF);

        TextView tRemote = Ui.text(a, "🎮 Remote TV Sedia", 13, 0xFF00B894, Ui.BOLD);
        banner.addView(tRemote);

        TextView dot = Ui.text(a, "  •  ", 13, Ui.MUTED, Ui.BOLD);
        banner.addView(dot);

        TextView tMouse = Ui.text(a, "🖱️ Sambung Tetikus untuk Game Melukis & Seret", 13, 0xFFE17055, Ui.BOLD);
        banner.addView(tMouse);

        LinearLayout.LayoutParams bannerLp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        bannerLp.bottomMargin = Ui.dp(24);
        col.addView(banner, bannerLp);

        // Action buttons row: Mula Belajar & Panduan Kawalan
        LinearLayout actions = new LinearLayout(a);
        actions.setOrientation(LinearLayout.HORIZONTAL);
        actions.setGravity(Gravity.CENTER);
        Ui.noClip(actions);

        // Primary: Mula Belajar
        TextView start = Ui.text(a, "🚀  MULA BELAJAR", 18, Ui.WHITE, Ui.BLACK);
        start.setBackground(Ui.gradient(new int[]{0xFFFF4F86, 0xFFFF9F1C}, Ui.dp(30)));
        start.setForeground(Ui.shine(Ui.dp(30)));
        start.setPadding(Ui.dp(32), Ui.dp(14), Ui.dp(32), Ui.dp(14));
        Ui.lift(start, 10, 0xFFFF4F86);
        start.setContentDescription("Mula Belajar");
        Ui.press(start, () -> {
            Sfx.tada();
            act.push(new HomeScreen(act));
        });
        actions.addView(start, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        startBtn = start;

        // Secondary: Panduan Kawalan
        TextView guide = Ui.text(a, "📖  Panduan Kawalan", 16, Ui.INK, Ui.BLACK);
        guide.setBackground(Ui.round(Ui.GLASS, Ui.dp(30)));
        guide.setPadding(Ui.dp(26), Ui.dp(14), Ui.dp(26), Ui.dp(14));
        Ui.lift(guide, 6, 0xFF3A86FF);
        guide.setContentDescription("Panduan Kawalan");
        Ui.press(guide, () -> {
            Sfx.ding();
            act.push(new GuideScreen(act));
        });
        LinearLayout.LayoutParams gp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        gp.leftMargin = Ui.dp(16);
        actions.addView(guide, gp);

        col.addView(actions, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    int[] palette() {
        return Theme.current.home;
    }

    @Override
    void onShown() {
        startBtn.requestFocus();
        Sfx.tada();

        // Cheerful mascot bobbing animation
        bob = ObjectAnimator.ofFloat(mascot, View.TRANSLATION_Y, -Ui.dp(8), Ui.dp(8));
        bob.setDuration(1100);
        bob.setRepeatMode(ValueAnimator.REVERSE);
        bob.setRepeatCount(ValueAnimator.INFINITE);
        bob.setInterpolator(new AccelerateDecelerateInterpolator());
        bob.start();
    }

    @Override
    void onHidden() {
        if (bob != null) {
            bob.cancel();
            bob = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER
                || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
            Sfx.tada();
            act.push(new HomeScreen(act));
            return true;
        }
        return false;
    }
}
