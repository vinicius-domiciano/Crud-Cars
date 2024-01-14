export const defaultValue = <T>(currentValue: T, defaultValue: T) => {
    console.log("default value", { isnull: isNullOrUndefined(currentValue), currentValue, defaultValue })
    return isNullOrUndefined(currentValue) ? defaultValue : currentValue;
};

export const isNullOrUndefined = <T>(value: T) => {
    if (value instanceof String) return String(value) === 'null';

    return value == null || value === undefined
};