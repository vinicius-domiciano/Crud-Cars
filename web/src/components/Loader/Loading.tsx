import React from "react";

import styles from "./style.module.css";

export default function Loading() {
    return (
        <div className={styles.container}>
            <div className={styles.box}>
                <span className={styles.loader}></span>
            </div>
        </div>
    );
}
