import { ComponentsModule } from './components/components.module';
import { ListModule } from './modules/list/list.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from '@angular/common/http';

@NgModule({
	declarations: [AppComponent],
	imports: [BrowserModule, AppRoutingModule, HttpClientModule, ListModule, ComponentsModule],
	providers: [],
	bootstrap: [AppComponent]
})
export class AppModule { }
