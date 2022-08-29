import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray} from '@angular/forms';
import { Router } from '@angular/router';
import { map } from 'rxjs';
import { AuthService } from '../auth.service';
import { UserDetail } from '../classes/user-detail';
import { AdminService } from '../service/admin.service';


@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  showPass: boolean = true;

  userField:string = '';
  checked:boolean = false;
  // user = window.localStorage.getItem('user');

  username!: string;
  password!: string;
  formData!: FormGroup;

  private userDetail: UserDetail = new UserDetail;
  constructor(private authService : AuthService, private router : Router,private adminService: AdminService) { }

  ngOnInit(): void {
    // if(this.user == null){
    //   this.userField = '';
    // }else{
    //   this.userField = this.user;
    // }

    this.formData = new FormGroup({
      username: new FormControl(""),
      password: new FormControl(""),
   });
  }


  // checkAll(e:any){
  //   if(e.target.checked){
  //     this.checked = true;
  //     console.log("checked" + this.userField)
  //   }else{
  //     this.checked = false;
  //     console.log("uncheck")
  //   }
  // }

  onClickSubmit(data: any) {
    // this.userName = data.userName;
    // this.password = data.password;
    console.log("Login page: " + typeof data.username);
    console.log("Login page: " + data.password);
    this.userDetail.username = data.username;
    this.userDetail.password = data.password;
    // if(this.checked){
    //   window.localStorage.setItem('user', this.userName);
    // }

    console.log("Login page: " + this.userDetail);
    console.log("Login page: " + typeof this.userDetail);

    this.adminService.login(this.userDetail).subscribe( response => {  
      console.log("respon data: " +response.role);
      console.log("respon data type: " + typeof response);

      if(response.status == "success") {
        localStorage.setItem('isUserLoggedIn',"true");
        if(response.role == "admin"){
          this.router.navigate(['/admin']); 
        }else if(response.role == "user"){
          this.router.navigate(['/']);
        }else{
          this.router.navigate(['/']);
        }
      }
      else{
        localStorage.setItem('isUserLoggedIn',"false");
      }
    },
    (error) => {
        console.log(error.error);
    });
 }
  toggleShowPass(){
      this.showPass = !this.showPass;
  }
}

