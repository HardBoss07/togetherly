import React from "react";

type InputFieldProps = {
    label: string;
    name: string;
    type?: string;
    value: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

export default function InputField({label, name, type = "text", value, onChange}: InputFieldProps) {
    return (
        <div>
            <label htmlFor={name}>{label}</label><br/>
            <input
                id={name}
                name={name}
                type={type}
                value={value}
                onChange={onChange}
            />
        </div>
    );
}