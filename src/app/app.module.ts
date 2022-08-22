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
import { LoginFormComponent } from './login-form/login-form.component';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ExpensGuard } from './expens.guard';
import { LogoutComponent } from './logout/logout.component';
import { AdminComponent } from './admin/admin.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    MainComponent,
    MainHomeComponent,
    MainServicesComponent,
    MainAboutComponent,
    LoginFormComponent,
    LogoutComponent,
    AdminComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    RouterModule.forRoot([
      { path: '', component: MainComponent },
      { path: 'services', component: MainServicesComponent },
      { path: 'about', component: MainAboutComponent },
      { path: 'login', component: LoginFormComponent },
      { path: 'logout', component: LogoutComponent },
      { path: 'admin', component: AdminComponent, canActivate: [ExpensGuard]},
      // { path: 'expenses/detail/:id', component: ExpenseEntryComponent, canActivate: [ExpensGuard]},
      // { path: '', redirectTo: 'expenses', pathMatch: 'full' }
    ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
