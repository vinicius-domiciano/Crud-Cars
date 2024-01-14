import React from "react";
import Input from "../Input";

import styles from "./style.module.css";

interface PropertiesMenu {
    buttonCreateName: string;
    onClickInsert: () => void;
}

export default function TopFindAndCreateMenu({
    buttonCreateName,
    onClickInsert,
}: PropertiesMenu) {
    return (
        <div id={styles.menuContainer}>
            <div>
                <Input
                    labelName="Find"
                    value=""
                    stylesName={[styles.inputFind]}
                    name="FindTopMenu"
                    validErrors={false}
                />
            </div>
            <button
                onClick={onClickInsert}
                className={styles.buttonCreate}
                name={`btn-${buttonCreateName
                    .replaceAll(" ", "-")
                    .toLocaleLowerCase()}`}
            >
                <i className={`${styles.buttonTopIcon} ${styles.icon}`}></i>
                {buttonCreateName}
            </button>
        </div>
    );
}
