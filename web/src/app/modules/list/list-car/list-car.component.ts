import { Component, OnInit } from '@angular/core';
import { ListCar } from 'src/app/shared/models/cars';
import { CarService } from 'src/app/shared/services/car/car.service';

@Component({
  selector: 'app-list-car',
  templateUrl: './list-car.component.html',
  styleUrls: ['./list-car.component.css']
})
export class ListCarComponent implements OnInit {
  public cars: ListCar[] = [];

  constructor(private carService: CarService) { }

  ngOnInit(): void {
    this.listCars();
  }

  async listCars(): Promise<void> {
    try {
      this.cars = await this.carService.listCars();
      console.info('Listing cars...', this.cars);
    } catch (error) {
      console.error('error of list cars', error);
    }
  }

  async deleteCar(id: number) {
    try {
      await this.carService.deleteCar(id);
      this.cars = this.cars.filter(car => car.id !== id);
    } catch (error) {
      console.error('error od delete car', { carId: id, error })
    }
  }
}
