package com.belajarceria.kids;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Control guide screen:
 * Clearly details how the app works with TV Remote arrow keys,
 * and when to plug in a USB or wireless mouse for interactive games.
 */
final class GuideScreen extends Screen {
    private final View confirmBtn;

    GuideScreen(MainActivity a) {
        super(a);
        LinearLayout col = new LinearLayout(a);
        col.setOrientation(LinearLayout.VERTICAL);
        Ui.noClip(col);

        // Header bar with back chevron
        col.addView(header("Panduan Kawalan & Cara Guna", 0xFF3A86FF),
                new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        HorizontalScrollView hsv = new HorizontalScrollView(a);
        hsv.setFillViewport(true);
        hsv.setHorizontalScrollBarEnabled(false);
        Ui.noClip(hsv);

        LinearLayout row = new LinearLayout(a);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setPadding(Ui.dp(20), Ui.dp(10), Ui.dp(20), Ui.dp(16));
        Ui.noClip(row);

        int screenW = getResources().getDisplayMetrics().widthPixels;
        int cardW = Math.max(Ui.dp(300), Math.min(Ui.dp(480), (screenW - Ui.dp(80)) / 2));
        int cardH = (int) Math.min(Math.max(Ui.shortSide(a) * 0.66f, Ui.dp(280)), Ui.dp(440));

        // Card 1: Remote TV
        row.addView(createRemoteCard(a, cardW, cardH));

        // Spacer between cards
        View gap = new View(a);
        row.addView(gap, new LinearLayout.LayoutParams(Ui.dp(16), 1));

        // Card 2: Mouse
        row.addView(createMouseCard(a, cardW, cardH));

        hsv.addView(row, new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        col.addView(hsv, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));

        // Bottom CTA: Faham, Jom Main!
        LinearLayout bottomBar = new LinearLayout(a);
        bottomBar.setGravity(Gravity.CENTER);
        bottomBar.setPadding(0, Ui.dp(4), 0, Ui.dp(14));
        Ui.noClip(bottomBar);

        TextView ok = Ui.text(a, "Faham, Jom Belajar! 🌟", 17, Ui.WHITE, Ui.BLACK);
        ok.setBackground(Ui.gradient(new int[]{0xFF00B894, 0xFF0984E3}, Ui.dp(26)));
        ok.setForeground(Ui.shine(Ui.dp(26)));
        ok.setPadding(Ui.dp(36), Ui.dp(12), Ui.dp(36), Ui.dp(12));
        Ui.lift(ok, 8, 0xFF00B894);
        ok.setContentDescription("Faham Jom Belajar");
        Ui.press(ok, () -> {
            Sfx.ding();
            act.back();
        });
        bottomBar.addView(ok, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        confirmBtn = ok;
        col.addView(bottomBar, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        addView(col, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    private View createRemoteCard(Context c, int w, int h) {
        LinearLayout card = new LinearLayout(c);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(Ui.dp(22), Ui.dp(18), Ui.dp(22), Ui.dp(18));
        card.setBackground(Ui.round(Ui.GLASS, Ui.dp(28)));
        Ui.lift(card, 10, 0xFF00B894);
        card.setLayoutParams(new LinearLayout.LayoutParams(w, h));

        // Header inside card
        LinearLayout ch = new LinearLayout(c);
        ch.setOrientation(LinearLayout.HORIZONTAL);
        ch.setGravity(Gravity.CENTER_VERTICAL);

        TextView icon = Ui.text(c, "🎮", 30, Ui.INK, null);
        icon.setBackground(Ui.oval(new int[]{0xFFE8F8F5, 0xFFD1F2EB}));
        Ui.lift(icon, 3, 0xFF00B894);
        ch.addView(icon, new LinearLayout.LayoutParams(Ui.dp(48), Ui.dp(48)));

        LinearLayout titles = new LinearLayout(c);
        titles.setOrientation(LinearLayout.VERTICAL);
        titles.setPadding(Ui.dp(12), 0, 0, 0);

        TextView t = Ui.text(c, "Kawalan Remote TV", 18, Ui.INK, Ui.BLACK);
        titles.addView(t);

        TextView b = Ui.badge(c, "✓ Tanpa Tetikus", true);
        titles.addView(b, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ch.addView(titles);
        card.addView(ch);

        // Instruction lines
        ScrollView sv = new ScrollView(c);
        sv.setVerticalScrollBarEnabled(false);
        LinearLayout list = new LinearLayout(c);
        list.setOrientation(LinearLayout.VERTICAL);
        list.setPadding(0, Ui.dp(12), 0, 0);

        addItem(list, "⬅️ ➡️", "Anak Panah Kiri / Kanan", "Tukar kad seterusnya & sebelumnya dalam Kad Belajar.");
        addItem(list, "⬆️ ⬇️", "Anak Panah Atas / Bawah", "Pilih jawapan Kuiz Ujian Diagnostik & navigasi menu.");
        addItem(list, "🔘 OK", "Butang OK / Tengah", "Ulang sebut suara (TTS) & sahkan jawapan kuiz.");
        addItem(list, "↩️ Back", "Butang Undur (Back)", "Kembali ke menu atau topik sebelumnya.");

        TextView readyTag = Ui.text(c, "🎮 Sesuai untuk: Kad Belajar, Kuiz Diagnostik, Cakap Mandarin & Muzik.",
                12, 0xFF00897B, Ui.BOLD);
        readyTag.setBackground(Ui.round(0x1F00B894, Ui.dp(12)));
        readyTag.setPadding(Ui.dp(12), Ui.dp(8), Ui.dp(12), Ui.dp(8));
        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        rp.topMargin = Ui.dp(8);
        list.addView(readyTag, rp);

        sv.addView(list);
        card.addView(sv, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        return card;
    }

    private View createMouseCard(Context c, int w, int h) {
        LinearLayout card = new LinearLayout(c);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(Ui.dp(22), Ui.dp(18), Ui.dp(22), Ui.dp(18));
        card.setBackground(Ui.round(Ui.GLASS, Ui.dp(28)));
        Ui.lift(card, 10, 0xFFFF7675);
        card.setLayoutParams(new LinearLayout.LayoutParams(w, h));

        // Header inside card
        LinearLayout ch = new LinearLayout(c);
        ch.setOrientation(LinearLayout.HORIZONTAL);
        ch.setGravity(Gravity.CENTER_VERTICAL);

        TextView icon = Ui.text(c, "🖱️", 30, Ui.INK, null);
        icon.setBackground(Ui.oval(new int[]{0xFFFFEAA7, 0xFFFDCB6E}));
        Ui.lift(icon, 3, 0xFFFF7675);
        ch.addView(icon, new LinearLayout.LayoutParams(Ui.dp(48), Ui.dp(48)));

        LinearLayout titles = new LinearLayout(c);
        titles.setOrientation(LinearLayout.VERTICAL);
        titles.setPadding(Ui.dp(12), 0, 0, 0);

        TextView t = Ui.text(c, "Tetikus Tambahan (Mouse)", 18, Ui.INK, Ui.BLACK);
        titles.addView(t);

        TextView b = Ui.badge(c, "⚡ Perlu Tetikus / Touch", false);
        titles.addView(b, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        ch.addView(titles);
        card.addView(ch);

        // Instruction lines
        ScrollView sv = new ScrollView(c);
        sv.setVerticalScrollBarEnabled(false);
        LinearLayout list = new LinearLayout(c);
        list.setOrientation(LinearLayout.VERTICAL);
        list.setPadding(0, Ui.dp(12), 0, 0);

        addItem(list, "✏️", "Melukis & Menyurih (Draw)", "Klik & gerakkan tetikus mengikut bentuk garisan huruf / nombor.");
        addItem(list, "🧩", "Seret & Lepas (Drag)", "Klik dan heret jubin huruf ke kotak bayang yang tepat.");
        addItem(list, "🔗", "Padankan Pasangan (Match)", "Klik dan tarik garisan dari gambar ke perkataan.");
        addItem(list, "🎹", "Muzik Bebas Interaktif", "Klik kekunci piano, petik tali gitar, dan palu dram secara bebas.");

        TextView tipTag = Ui.text(c, "💡 Petua: Cucuk tetikus USB atau wireless (2.4G/Bluetooth) ke port USB Android TV!",
                12, 0xFFD35400, Ui.BOLD);
        tipTag.setBackground(Ui.round(0x1FFF7675, Ui.dp(12)));
        tipTag.setPadding(Ui.dp(12), Ui.dp(8), Ui.dp(12), Ui.dp(8));
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        tp.topMargin = Ui.dp(8);
        list.addView(tipTag, tp);

        sv.addView(list);
        card.addView(sv, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1f));
        return card;
    }

    private void addItem(LinearLayout parent, String icon, String title, String desc) {
        Context c = parent.getContext();
        LinearLayout row = new LinearLayout(c);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(0, Ui.dp(4), 0, Ui.dp(4));

        TextView ic = Ui.text(c, icon, 16, Ui.INK, Ui.BOLD);
        ic.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        row.addView(ic, new LinearLayout.LayoutParams(Ui.dp(42), LayoutParams.WRAP_CONTENT));

        LinearLayout col = new LinearLayout(c);
        col.setOrientation(LinearLayout.VERTICAL);
        col.setPadding(Ui.dp(4), 0, 0, 0);

        TextView h = Ui.text(c, title, 14, Ui.INK, Ui.BOLD);
        h.setGravity(Gravity.START);
        col.addView(h);

        TextView d = Ui.text(c, desc, 12, Ui.MUTED, null);
        d.setGravity(Gravity.START);
        col.addView(d);

        row.addView(col, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
        parent.addView(row);
    }

    @Override
    int[] palette() {
        return Theme.current.home;
    }

    @Override
    void onShown() {
        confirmBtn.requestFocus();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER
                || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
            Sfx.ding();
            act.back();
            return true;
        }
        return false;
    }
}
