import React from "react";
import { isNullOrUndefined } from "../../utils/stringUtils";

import styles from "./style.module.css";

interface Params<T> {
    fields: Array<string>;
    value: Array<Value<T>>;
    actions: Actions;
    onUpdateAction?: (content: Value<T>) => void;
    onFindAction?: (content: Value<T>) => void;
    onDeleteAction?: (content: Value<T>) => void;
}

interface Actions {
    update: boolean;
    delete: boolean;
    find: boolean;
}

type Value<T> = { [key: string]: T };

export default function TableCRUD<T>(params: Params<T>) {
    function convertTo(value: string, index: number) {
        return <div key={`${index}_fieldName${value}`}>{value}</div>;
    }

    function convertValues(value: Value<T>, index: number) {
        const keys = Object.keys(value);
        const elements = params.fields.map((field, i) => {
            field = field.toLocaleLowerCase();
            return (
                <div id={`field_${index}_${i}`} key={`field_${index}_${i}`}>
                    {!keys.some((key) => key === field)
                        ? ""
                        : isNullOrUndefined(value[field])
                        ? ""
                        : String(value[field])}
                </div>
            );
        });

        elements.push(
            <div
                key={`actions_${index}`}
                style={{ display: "grid" }}
                className={styles.fieldNameActions}
            >
                {params.actions.find ? (
                    <button
                        style={{
                            backgroundImage:
                                "url(/icons/svg/find-button-icon.svg)",
                        }}
                        key={`action_find_${index}`}
                        id={`action_find_${index}`}
                        onClick={() =>
                            startUndefinedFunction(params.onFindAction, value)
                        }
                    />
                ) : (
                    <span></span>
                )}

                {params.actions.update ? (
                    <button
                        style={{
                            backgroundImage:
                                "url(/icons/svg/edit-button-icon.svg)",
                        }}
                        key={`action_update_${index}`}
                        id={`action_update_${index}`}
                        onClick={() =>
                            startUndefinedFunction(params.onUpdateAction, value)
                        }
                    />
                ) : (
                    <span></span>
                )}
                {params.actions.delete ? (
                    <button
                        style={{
                            backgroundImage:
                                "url(/icons/svg/delete-button-icon.svg)",
                        }}
                        key={`action_delete_${index}`}
                        id={`action_delete_${index}`}
                        onClick={() =>
                            startUndefinedFunction(params.onDeleteAction, value)
                        }
                    />
                ) : (
                    <span></span>
                )}
            </div>
        );

        return elements;
    }

    return (
        <div id={styles.container}>
            <div className={styles.containerField}>
                {params.fields.map(convertTo)}
                <div key={`${params.fields.length}_fieldNameActions`}>
                    Actions
                </div>
            </div>
            <div
                className={styles.containerValue}
                style={{
                    gridTemplateColumns: `repeat(${
                        params.fields.length + 1
                    }, 1fr)`,
                }}
            >
                {params.value.map(convertValues)}
            </div>
        </div>
    );
}

function startUndefinedFunction<T>(funtion?: Function, param?: Value<T>): void {
    if (funtion !== undefined) funtion(param);
}
