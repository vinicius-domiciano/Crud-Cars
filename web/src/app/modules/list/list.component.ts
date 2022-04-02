import { Component, OnInit } from '@angular/core';
import { CarService } from 'src/app/shared/services/car/car.service';

@Component({
	selector: 'app-list',
	templateUrl: './list.component.html',
	styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {

	constructor(private carService: CarService) { }

	ngOnInit(): void { }
}
