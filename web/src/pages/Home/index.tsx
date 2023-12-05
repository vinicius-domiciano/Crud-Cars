import React from "react";

import styles from "./style.module.css";

export default function Home() {
    return (
        <div className="container">
            <div id={styles.boxContainer}>
                <h1>Welcome!</h1>
                <br />
                <h3>Please, select in menu an action</h3>
            </div>
        </div>
    );
}
