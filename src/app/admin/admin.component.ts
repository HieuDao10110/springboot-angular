import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  isUserLoggedIn = false;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    let storeData = localStorage.getItem("isUserLoggedIn");
      console.log("StoreData: " + storeData);

      if( storeData != null && storeData == "true")
         this.isUserLoggedIn = true;
      else
         this.isUserLoggedIn = false;
  }

}
