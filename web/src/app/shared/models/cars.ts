export interface Car {
	id: number;
	name?: string;
	year?: number;
	dateCreated?: string;
	dateUpdated?: string;
	company?: string;
	price?: number;
}

export interface ListCar {
	id: number;
	name: string;
	price: number;
	company: string;
	dateCreated: string;
}

export interface SendCar {
	id: number;
	name: string;
	price: number;
	company: string;
	year: number;
}
