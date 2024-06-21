import { useEffect, useState } from "react";
import { FaFemale } from "react-icons/fa";
import { FaMale } from "react-icons/fa";
import { GiUnicorn } from "react-icons/gi";
interface Props {
  jwt: string;
}

class PhotoDto {
  id: number;
  profiledId: number;
  name: string;
  isProfilePhoto: boolean;

  constructor(jsonObject) {
    this.id = jsonObject.id;
    this.profiledId = jsonObject.profiledId;
    this.name = jsonObject.name;
    this.isProfilePhoto = jsonObject.isProfilePhoto;
  }
}

class ProfileDto {
  id: number;
  birthdate: Date;
  nickname: string;
  hornlength: number;
  gender: number;
  attractedToGender: string;
  description: string;
  lastseen: Date;
  age: number;
  lastseenDaysAgo: number;
  photos: PhotoDto[];

  constructor(json: string) {
    const jsonObject = JSON.parse(json);

    this.id = jsonObject.id;

    this.nickname = jsonObject.nickname;
    this.hornlength = jsonObject.hornlength;
    this.description = jsonObject.description;
    this.lastseen = new Date(jsonObject.lastseen);
    this.birthdate = new Date(jsonObject.birthdate);
    this.gender = jsonObject.gender;

    if (!jsonObject.attractedToGender) {
      this.attractedToGender = "Bisexuell";
    } else if (jsonObject.attractedToGender == jsonObject.gender) {
      this.attractedToGender = "Homosexuell";
    } else {
      this.attractedToGender = "Heterosexuell";
    }

    this.age = calculateAge(this.birthdate);
    this.lastseenDaysAgo = calculateDaysDiff(this.lastseen);

    this.photos = jsonObject.photos.map((photo) => {
      return new PhotoDto(photo);
    });
  }
}

function calculateAge(birthday: Date) {
  return Math.abs(
    new Date(Date.now() - birthday.getTime()).getUTCFullYear() - 1970
  );
}

function calculateDaysDiff(date: Date) {
  const differenceBtwDates = new Date().getTime() - date.getTime();

  const aDayInMs = 24 * 60 * 60 * 1000;

  return Math.round(differenceBtwDates / aDayInMs);
}

const Profile = ({ jwt }: Props) => {
  const [profile, setProfile] = useState<ProfileDto>();
  const [photoBlob, setPhotoBlob] = useState<string[]>([]);


  useEffect(() => {
    fetch("http://localhost:8080/api/profiles/self" , {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + jwt,
      },
    })
      .then((response) => response.json())
      .then((fetchedData) => {
        const json = JSON.stringify(fetchedData);
        const newProfile = new ProfileDto(json);
   

        newProfile.photos.forEach((photo) => {
          console.log("Y");
          const fetchPhotos = async () => {
            const src = "http://localhost:8080/api/photos/" + photo.name;
            const options = {
              headers: {
                Authorization: `Bearer ` + jwt,
              },
            };

            await fetch(src, options)
              .then((res) => res.blob()) 
              .then((blob) => {
                const x: string = URL.createObjectURL(blob);
                console.log(typeof x);
                console.log(typeof photoBlob[0]);
                photoBlob.push(x);
                setPhotoBlob(photoBlob);
                setProfile(newProfile);
              });
          };

          fetchPhotos();
        });
      })
      .catch((error) => {
        console.error("Fehler beim Laden der Daten:", error);
      });
  }, []);

  return (
    <div className="content-container ">
      <div className="profile-container">
        {profile && profile.photos  && (
          <>
            <div id="carouselExample" className="carousel slide">
              <div className="carousel-inner"> 
                {/* {photoBlob.map((blob, index) => (
                  
                  <div className="carousel-item profile-photo ${index === 0 ? 'active' : ''}" key={index}>
                    <img src={blob} className="d-block w-100"/>
                  </div>
        
                ))} */}
                <div className="carousel-item active profile-photo">
                  <img src={photoBlob[0]} className="d-block w-100" alt="..." />
                </div>
                <div className="carousel-item profile-photo">
                  <img
                    src={photoBlob[1]}
                    className="d-block w-100 "
                    alt="..."
                  />
                </div>
                <div className="carousel-item profile-photo">
                  <img
                    src={photoBlob[2]}
                    className="d-block w-100 "
                    alt="..."
                  />
                </div>
              </div>
              <button
                className="carousel-control-prev"
                type="button"
                data-bs-target="#carouselExample"
                data-bs-slide="prev"
              >
                <span
                  className="carousel-control-prev-icon"
                  aria-hidden="true"
                ></span>
                <span className="visually-hidden">Previous</span>
              </button>
              <button
                className="carousel-control-next"
                type="button"
                data-bs-target="#carouselExample"
                data-bs-slide="next"
              >
                <span
                  className="carousel-control-next-icon"
                  aria-hidden="true"
                ></span>
                <span className="visually-hidden">Next</span>
              </button>
            </div>
            {/* <img src={photoBlob} className="profile-photo" /> */}
            <h3>
              {profile.nickname} (
              {profile.gender == 1 ? (
                <FaFemale className="female-icon" />
              ) : (
                <FaMale className="male-icon" />
              )}{" "}
              {profile.age}
              ,
              <GiUnicorn className="unicorn-icon" />
              {profile.hornlength})
            </h3>
            <div className="user-stats">
              <p>Active {profile.lastseenDaysAgo} days ago</p>
            </div>
            <div className="user-stats">
              <p>{profile.attractedToGender}</p>
            </div>
            <div className="description">{profile.description}</div>
          </>
        )}
      </div>
    </div>
  );
};

export default Profile;
