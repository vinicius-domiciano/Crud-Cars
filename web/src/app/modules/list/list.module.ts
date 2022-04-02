import { ListComponent } from './list.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListCarComponent } from './list-car/list-car.component';

@NgModule({
  declarations: [ListComponent, ListCarComponent],
  imports: [CommonModule],
  exports: [ListComponent]
})
export class ListModule { }
