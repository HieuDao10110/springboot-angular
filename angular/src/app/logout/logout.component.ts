import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { AdminService } from '../service/admin.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private authService : AuthService,private adminService : AdminService, private router: Router) { }

  ngOnInit(): void {
    this.adminService.logout().subscribe(res =>{
      console.log("logout response status code" + res.status);
      if(res.status == 200){
        this.router.navigate(['/']);
      }
    });
  }
}
