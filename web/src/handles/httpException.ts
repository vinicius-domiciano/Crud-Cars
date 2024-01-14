export class HttpException implements Error {

    name: string;
    message: string;
    stack?: any;
    cause?: any;

    constructor(message: string) {
        this.name = "Http Error"
        this.message = message;
    }

} 