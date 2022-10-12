package ar.com.gmn.android.view.component;
import ar.com.gmn.android.core.Numero;
import ar.com.gmn.android.core.Respuesta;
public class TRRespuesta extends android.widget.TableRow {
    private android.widget.TextView turno;

    private ar.com.gmn.android.view.component.NumeroView numero;

    private android.widget.TextView bien;

    private android.widget.TextView regular;

    public TRRespuesta(android.content.Context context, ar.com.gmn.android.core.Respuesta r, int style) {
        super(context);
        turno = new android.widget.TextView(context);
        numero = new ar.com.gmn.android.view.component.NumeroView(context, r.getNumero());
        bien = new android.widget.TextView(context);
        bien.setText(r.getCorrectos().toString());
        regular = new android.widget.TextView(context);
        regular.setText(r.getRegulares().toString());
        turno.setTextAppearance(style);
        bien.setTextAppearance(context, style);
        regular.setTextAppearance(context, style);
        numero.setTextAppearance(context, style);
        turno.setGravity(android.view.Gravity.CENTER);
        bien.setGravity(android.view.Gravity.CENTER);
        regular.setGravity(android.view.Gravity.CENTER);
        numero.setGravity(android.view.Gravity.CENTER);
        this.addView(turno);
        this.addView(numero);
        this.addView(bien);
        this.addView(regular);
    }

    public void setTextFont(android.graphics.Typeface type) {
        turno.setTypeface(type);
        bien.setTypeface(type);
        regular.setTypeface(type);
        numero.setTypeface(type);
    }

    public void setBien(java.lang.Integer i) {
        this.bien.setText(i.toString());
    }

    public void setRegular(java.lang.Integer i) {
        this.regular.setText(i.toString());
    }

    public void setNumero(ar.com.gmn.android.core.Numero n) {
        this.numero = new ar.com.gmn.android.view.component.NumeroView(this.getContext(), n);
    }

    public void setTurno(java.lang.Integer i) {
        this.turno.setText(i.toString());
    }
}