import React from "react";

import styles from "./style.module.css";

interface Properties extends React.PropsWithChildren {}

export default function BodyModal(props: Properties) {
    return <div className={styles.body}>{props.children}</div>;
}
