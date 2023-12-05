import React from "react";

import styles from "./style.module.css";

interface ModalProps extends React.PropsWithChildren {
    width: string;
    height: string;
}

export default function Modal({ width, height, ...props }: ModalProps) {
    return (
        <div className={styles.modalContainer}>
            <div style={{ width, height }} className={styles.modalBox}>
                {props.children}
            </div>
        </div>
    );
}
