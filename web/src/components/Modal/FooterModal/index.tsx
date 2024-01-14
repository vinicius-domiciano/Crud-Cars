import React from "react";

import styles from "./style.module.css";

interface Properties {
    buttons: Array<ButtonProperties>;
}

interface ButtonProperties {
    name: string;
    color: string;
    outline?: boolean;
    onAction: () => void;
}

export default function FooterModal({ buttons }: Properties) {
    function createButton(button: ButtonProperties, index: number) {
        return (
            <button
                key={`btn_footer_modal_${index}`}
                id={`btn_footer_modal_${index}`}
                style={{
                    backgroundColor: button.outline
                        ? "transparent"
                        : button.color,
                    border: button.outline
                        ? `solid 2px ${button.color}`
                        : `0px`,
                    color: button.outline ? button.color : "#FFFFFF",
                }}
                className={styles.button}
                onClick={button.onAction}
            >
                {button.name}
            </button>
        );
    }

    return <div className={styles.footer}>{buttons.map(createButton)}</div>;
}
