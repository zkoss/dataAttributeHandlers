package org.zkoss.handlers.chillworld.qrcode.components;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.A;

/**
 *
 * @author cossaer.f
 */
public class QrCode extends A implements AfterCompose {

    @Override
    public void afterCompose() {
        this.setClientAttribute("data-qrcode-href", "");
    }

}
