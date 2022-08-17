import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators, FormArray} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  showPass: boolean = true;

  userField:string = '';
  checked:boolean = false;
  user = window.localStorage.getItem('user');

  userName!: string;
  password!: string;
  formData!: FormGroup;

  constructor(private authService : AuthService, private router : Router) { }

  ngOnInit(): void {
    if(this.user == null){
      this.userField = '';
    }else{
      this.userField = this.user;
    }

    this.formData = new FormGroup({
      userName: new FormControl(this.userField),
      password: new FormControl(""),
   });
  }


  checkAll(e:any){
    if(e.target.checked){
      this.checked = true;
      console.log("checked" + this.userField)
    }else{
      this.checked = false;
      console.log("uncheck")
    }
  }



  onClickSubmit(data: any) {
    this.userName = data.userName;
    this.password = data.password;

    if(this.checked){
      window.localStorage.setItem('user', this.userName);
    }

    console.log("Login page: " + this.userName);
    console.log("Login page: " + this.password);

    this.authService.login(this.userName, this.password).subscribe( (data: string) => { 
      console.log("Is Login Success: " + data); 
      if(data) this.router.navigate(['/']); 
    });

 }
  toggleShowPass(){
      this.showPass = !this.showPass;
  }
}
