import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { MainComponent } from './main/main.component';
import { MainHomeComponent } from './main-home/main-home.component';
import { MainServicesComponent } from './main-services/main-services.component';
import { MainAboutComponent } from './main-about/main-about.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainComponent,
    MainHomeComponent,
    MainServicesComponent,
    MainAboutComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot([
      { path: '', component: MainHomeComponent },
      { path: 'services', component: MainServicesComponent },
      { path: 'about', component: MainAboutComponent },
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
