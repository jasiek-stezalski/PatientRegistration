import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {Router} from '@angular/router';
import {User} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(public userService: UserService, public router: Router) {
  }

  ngOnInit() {
    this.user.role = 'USER';
    this.user.insurance = false;
  }

  register() {
    if (this.user.password == this.user.confirmPassword) {
      this.userService.createUser(this.user).subscribe(() => {
          this.router.navigate(['/login']);
        }, err => {
          console.log(err);
          if (err.valueOf().status === 409)
            this.errorMessage = 'Błąd : Taki użytkownik już istnieje';
          if (err.valueOf().status === 406)
            this.errorMessage = 'Błąd : Ten Pesel jest zajęty. Sprawdź czy posiadasz już konto';
          if (err.valueOf().status === 404)
            this.errorMessage = 'Błąd : Podałeś niepoprawne dane osobowe';
        }
      );
    } else {
      this.errorMessage = 'Błąd : Hasła muszą być takie same';
    }
  }

}
