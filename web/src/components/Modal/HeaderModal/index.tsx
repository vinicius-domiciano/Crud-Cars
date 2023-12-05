import React from "react";

import styles from "./style.module.css";

interface Properties {
    title: string;
    subtitle?: string;
    closeButton?: boolean;
    onClickCloseButton?: () => void;
}

export default function HeaderModal({
    title,
    subtitle,
    closeButton,
    onClickCloseButton,
}: Properties) {
    return (
        <div className={styles.header}>
            <h1>{title}</h1>
            {subtitle ? <h3>{subtitle}</h3> : <span></span>}
            {closeButton ? (
                <button
                    className={styles.closeButton}
                    onClick={onClickCloseButton}
                />
            ) : (
                <span></span>
            )}
        </div>
    );
}
