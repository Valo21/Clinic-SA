export enum ToastType {
    INFO,
    WARNING,
    DANGER
}

export class Toast {
    private _message: string
    private _type: ToastType

    constructor(message: string, type: ToastType) {
        this._message = message;
        this._type = type;
    }

    get message(): string {
        return this._message;
    }

    set message(value: string) {
        this._message = value;
    }

    get type(): ToastType {
        return this._type;
    }

    set type(value: ToastType) {
        this._type = value;
    }
}
