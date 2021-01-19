import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IProject {
  id?: number;
  name?: string;
  description?: string;
  startDate?: Moment;
  endDate?: Moment;
  isActive?: boolean;
  users?: IUser[];
  activities?: IActivity[];
}

export class Project implements IProject {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public startDate?: Moment,
    public endDate?: Moment,
    public isActive?: boolean,
    public users?: IUser[],
    public activities?: IActivity[]
  ) {
    this.isActive = this.isActive || false;
  }
}
