import { ListCar } from './../../models/cars';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
	providedIn: 'root'
})
export class CarService {

	private readonly BASE_API = `http://127.0.0.1:8080/api/cars`;

	constructor(private httpClient: HttpClient) { }

	listCars(): Promise<ListCar[]> {
		return this.httpClient.get<ListCar[]>(this.BASE_API).toPromise();
	}

	deleteCar(id: number): Promise<void> {
		return this.httpClient.delete<void>(`${this.BASE_API}/${id}`).toPromise();
	}
}
