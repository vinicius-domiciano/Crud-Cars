import { HorizontalComponent } from './header/horizontal/horizontal.component';
import { VerticalComponent } from './header/vertical/vertical.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    HeaderComponent,
    VerticalComponent,
    HorizontalComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    HeaderComponent
  ]
})
export class ComponentsModule { }
