import React, { useEffect, useState } from "react";

import styles from "./style.module.css";

export enum ErrorType {
    EMPTY,
    MAX,
    MIN,
}

interface Properties extends React.InputHTMLAttributes<HTMLInputElement> {
    labelName: string;
    validErrors: boolean;
    stylesName: string[];
    errors?: ValidError[];
    setContainsErros?: React.Dispatch<React.SetStateAction<boolean>>;
}

export interface ValidError {
    error: ErrorType;
    propert?: string;
    message: string;
}

export default function Input({
    labelName,
    errors,
    validErrors,
    setContainsErros,
    ...props
}: Properties) {
    const [onError, setOnError] = useState(false);
    const [messageError, setMessageError] = useState("");

    useEffect(() => {
        if (validErrors) valid(props.value as string);
    }, [validErrors]);

    function valid(value: string) {
        if (!errors) return;

        for (const valid of errors) {
            if (isAnError(value, valid)) {
                setOnError(true);
                if (setContainsErros) setContainsErros(true);
                setMessageError(valid.message);

                return;
            }
        }

        if (setContainsErros) setContainsErros(false);
        setOnError(false);
    }

    function isAnError(value: string, valid: ValidError) {
        switch (valid.error) {
            case ErrorType.EMPTY:
                return isEmpty(value);
            case ErrorType.MAX:
                return biggerThen(value, valid);
            case ErrorType.MIN:
                return lessThan(value, valid);
            default:
                return false;
        }
    }

    return (
        <div>
            <input
                onBlur={(ev) => valid(ev.target.value)}
                className={`${
                    onError ? styles.inputError : styles.input
                } ${props.stylesName.join(" ")}`}
                {...props}
            />
            <span className={styles.floatingLabel}>{labelName}</span>
            {onError ? (
                <div className={styles.errorBox}>
                    <div className={`icon ${styles.warning}`}></div>
                    {messageError}
                </div>
            ) : (
                <span></span>
            )}
        </div>
    );
}

function isEmpty(value: string) {
    return value.length === 0;
}

function lessThan(value: string, valid: ValidError) {
    return valid.propert && value.length < Number.parseInt(valid.propert);
}

function biggerThen(value: string, valid: ValidError) {
    return valid.propert && value.length > Number.parseInt(valid.propert);
}
