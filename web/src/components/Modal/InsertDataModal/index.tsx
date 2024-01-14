import React from "react";

import styles from "./style.module.css";

interface Properties extends React.PropsWithChildren {
    useHBF: boolean;
}

export default function InsertDataModal({ useHBF, ...props }: Properties) {
    return (
        <div
            style={{
                display: useHBF ? "grid" : "block",
                gridTemplateColumns: "1fr",
                gridTemplateRows: "auto 1fr auto",
            }}
            className={styles.insertContainer}
        >
            {props.children}
        </div>
    );
}
